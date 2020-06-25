
/* eslint-disable no-unused-vars */
import { Component, OnInit } from "@angular/core";
import { DataSource } from "ng2-smart-table/lib/lib/data-source/data-source";
import { NbToastrService } from "@nebular/theme";
import { Router } from "@angular/router";
import { RoleData, Role } from "../../../@core/interfaces/common/role";

@Component({
  selector: "roles-table",
  templateUrl: "./roles-table.component.html",
  styleUrls: ["./roles-table.component.scss"]
})
export class RolesTableComponent implements OnInit {
  selectRow: any;
  settings = {
    actions: {
      columnTitle: "Actions",
      position: "left", // left|right
    },
    add: {
      addButtonContent: "<i class=\"nb-plus\"></i>",
      createButtonContent: "<i class=\"nb-checkmark\"></i>",
      cancelButtonContent: "<i class=\"nb-close\"></i>",
      confirmCreate: true,
    },
    edit: {
      editButtonContent: "<i class=\"nb-edit\"></i>",
      saveButtonContent: "<i class=\"nb-checkmark\"></i>",
      cancelButtonContent: "<i class=\"nb-close\"></i>",
      confirmSave: true,
    },
    delete: {
      deleteButtonContent: "<i class=\"nb-trash\"></i>",
      confirmDelete: true,
    },
    columns: {
      // id: {
      //   title: "id",
      //   type: "string",
      // },
      // isDefault: {
      //   title: "is_default",
      //   type: "string",
      // },
      name: {
        title: "name",
        type: "string",
      },
    },
  };

  setSelecRow(event: any): void {
    if (event.isSelected) {
      this.selectRow = event;
    } else {
      this.selectRow = null;
    }
  }

  source: DataSource;
  constructor(private roleData: RoleData, private toasterService: NbToastrService, private router: Router, ) { }

  ngOnInit(): void {
    this.source = this.roleData.DataSourceroles;
  }
  convertToRole(value: any): Role {
    let role: Role = value;
    role.isDefault = false;
    return role;
  }
  onDeleteConfirm(event: any): void {
    if (window.confirm("Are you sure you want to delete?")) {
      this.roleData.deleteroles(event.data.id).subscribe((result: any) => {
        this.handleSuccessResponse();
        event.confirm.resolve();
      },
        err => {
          this.handleWrongResponse(err.error);
          event.confirm.reject();
        });
    } else {
      event.confirm.reject();
    }
  }
  onSaveConfirm(event: any): void {
    if (window.confirm("Are you sure you want to save?")) {
      const user: Role = this.convertToRole(event.newData);
      this.roleData.updateroles(user).subscribe((result: any) => {
        event.confirm.resolve(result);
      },
        err => {
          this.handleWrongResponse(err.error.error);
          event.confirm.resolve(event.data);
        });
    } else {
      event.confirm.reject();
    }
  }
  onCreateConfirm(event: any): void {
    if (window.confirm("Are you sure you want to create?")) {
      const role: Role = this.convertToRole(event.newData);
      this.roleData.createroles(role).subscribe((result: any) => {
        event.confirm.resolve(result);
      },
        err => {
          this.handleWrongResponse(err.error.error);
          event.confirm.reject();
        });
    } else {
      event.confirm.reject();
    }
  }

  editbutton(): void {
    if (this.selectRow != null && this.selectRow.data != null) {
      var item: any = this.selectRow.data;
      this.router.navigate(["/pages/roles/roles-table-editbutton" + "/" + item.id]);
    } else {
      this.toasterService.warning("", "Not Data selected!");
    }
  }
  deletebutton(): void {
    if (this.selectRow != null && this.selectRow.data != null) {
      var event: any = this.selectRow;
      if (window.confirm("Are you sure you want to delete?")) {
        this.roleData.deleteroles(event.data.id).subscribe((result: any) => {
          this.handleSuccessResponse();
          this.ngOnInit();
        },
          err => {
            this.handleWrongResponse(err.error);
          });
      }
    } else {
      this.toasterService.warning("", "Not Data selected!");
    }
  }
  exportbutton(): void {
    this.roleData.exportroles(this.source.getFilter().filters, this.source.getSort()).subscribe((result: any) => {
      const link: any = document.createElement("a");
      const blob: any = new Blob([result.body]);
      link.setAttribute("href", window.URL.createObjectURL(blob));
      link.setAttribute("download", "RolesTable-" + new Date().toDateString() + ".csv");
      link.style.visibility = "hidden";
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    });
  }
  handleSuccessResponse(): void {
    this.toasterService.success("", "Data submission successful!");
  }

  handleWrongResponse(err: any): void {
    this.toasterService.danger(err, "Data submission fails!");
  }
}
