/* eslint-disable no-unused-vars */

import { Component, OnInit } from "@angular/core";
import { DataSource } from "ng2-smart-table/lib/lib/data-source/data-source";
import { UserData, User } from "../../../@core/interfaces/common/users";
import { NbToastrService } from "@nebular/theme";
import { Router } from "@angular/router";
@Component({
  selector: "ngx-users-table",
  templateUrl: "./users-table.component.html",
  styleUrls: ["./users-table.component.scss"]
})
export class UsersTableComponent implements OnInit {
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
    custom: {
      name: "my",
      title: "ddd",
      editButtonContent: "<i class=\"nb-edit\"></i>",
    },
    columns: {
      id: {
        title: "ID",
        type: "number",
        editable: false,
      },
      firstName: {
        title: "First Name",
        type: "string",
      },
      lastName: {
        title: "Last Name",
        type: "string",
      },
      login: {
        title: "Login",
        type: "string",
      },
      email: {
        title: "E-mail",
        type: "string",
      },
      age: {
        title: "Age",
        type: "number",
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
  toEdit(): void {
    if (this.selectRow != null && this.selectRow.data != null) {
      this.router.navigate(["/pages/users/useredit/" + this.selectRow.data.id]);
    } else {
      this.toasterService.warning("", `Not Data selected!`);
    }
  }

  source: DataSource;
  constructor(private usersService: UserData, private toasterService: NbToastrService, private router: Router, ) {
  }

  ngOnInit(): void {
    this.source = this.usersService.gridDataSource;
  }

  convertToUser(value: any): User {
    const user: User = value;
    return user;
  }
  onDeleteConfirm(event: any): void {
    if (window.confirm("Are you sure you want to delete?")) {
      this.usersService.delete(event.data.id).subscribe((result: any) => {
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

      const user: User = this.convertToUser(event.newData);
      this.usersService.update(user).subscribe((result: any) => {
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
      const user: User = this.convertToUser(event.newData);
      this.usersService.create(user).subscribe((result: any) => {
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

  handleSuccessResponse(): void {
    this.toasterService.success("", `Data submission successful!`);
  }

  handleWrongResponse(err: any): void {
    this.toasterService.danger(err, `Data submission fails!`);
  }
}

