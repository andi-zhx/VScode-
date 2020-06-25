
/* eslint-disable no-unused-vars */
import { Component, OnInit, OnDestroy } from "@angular/core";
import { GgRoleData, GgRole } from "../../../@core/interfaces/common/gen/ggRole";
import { takeUntil } from "rxjs/operators";
import { Router, ActivatedRoute } from "@angular/router";
import { NbToastrService } from "@nebular/theme";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Subject } from "rxjs";
import { NUMBERS_PATTERN,DOUBLE_PATTERN } from "../../../@auth/components";
import { DecimalPipe } from "@angular/common";
import { RoleData } from '../../../@core/interfaces/common/role';

@Component({
  selector: "roles-table-editbutton",
  templateUrl: "./roles-table-editbutton.component.html",
  styleUrls: ["./roles-table-editbutton.component.scss"]
})
export class RolesTableeditbuttonComponent implements OnInit, OnDestroy {
  rolesTableForm: FormGroup;
  protected readonly unsubscribe$ = new Subject<void>();

  get id(): any { return this.rolesTableForm.get("id"); }
  get isDefault(): any { return this.rolesTableForm.get("isDefault"); }
  get name(): any { return this.rolesTableForm.get("name"); }

  constructor(private ggRoleData: RoleData,
    private toasterService: NbToastrService,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder) {
      this.initRolesTableForm();
    }

  ngOnInit(): void {
    this.loadGgRoleData();
  }
  initRolesTableForm(): void {
    this.rolesTableForm = this.fb.group({

    id: this.fb.control("",[Validators.required,Validators.pattern(NUMBERS_PATTERN)]),
    isDefault: this.fb.control("",[]),
    name: this.fb.control("",[Validators.maxLength(255)]),
    });
  }
  loadGgRoleData(): void {
    // const id: number = parseInt(this.route.snapshot.paramMap.get("id"), 10);
    const id: any = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.loadGgRole(id);
    }
  }
  loadGgRole(id?: any): void {
    this.ggRoleData.getroles(id)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((data) => {
        this.setrolesTableForm(data);
      });
  }
  setrolesTableForm(data:any): void {
    this.rolesTableForm.setValue({
    id: data.id ? data.id : "",
    isDefault: data.isDefault ? data.isDefault : "",
    name: data.name ? data.name : "",
    });
  }
  convertToGgRole(value: any):  GgRole {
    const data:  GgRole = value;
    return data;
  }
  save(): void {
    const ggRole: GgRole = this.convertToGgRole(this.rolesTableForm.value);
    this.ggRoleData.updateroles(ggRole)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((data) => {
        this.handleSuccessResponse();
        this.initRolesTableForm();
        this.setrolesTableForm(data);
      },
        err => {
          this.handleWrongResponse(err);
        });
  }
  back(): void {
    this.router.navigate(["/pages/roles/rolestable"]);
  }
  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
  handleSuccessResponse(): void {
    this.toasterService.success("", "Data submission successful!");
  }

  handleWrongResponse(err: any): void {
    this.toasterService.danger(err, "Data submission fails!");
  }
}
