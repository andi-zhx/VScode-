/* eslint-disable no-unused-vars */
import { Component,  OnInit, OnDestroy } from "@angular/core";
import { FormGroup, Validators, FormBuilder } from "@angular/forms";
import { UserData, User } from "../../../@core/interfaces/common/users";

import { EMAIL_PATTERN, NUMBERS_PATTERN } from "../../../@auth/components";
import { Router } from "@angular/router";
import { NbToastrService } from "@nebular/theme";
import { takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";
@Component({
  selector: "ngx-user-new",
  templateUrl: "./user-new.component.html",
  styleUrls: ["./user-new.component.scss"]
})
export class UserNewComponent implements OnInit,OnDestroy {
  userForm: FormGroup;
  protected readonly unsubscribe$ = new Subject<void>();

  get firstName():any { return this.userForm.get("firstName"); }

  get lastName():any { return this.userForm.get("lastName"); }

  get login():any { return this.userForm.get("login"); }

  get email():any { return this.userForm.get("email"); }

  get age():any { return  this.userForm.get("age"); }

  get street():any { return this.userForm.get("address").get("street"); }

  get city():any { return this.userForm.get("address").get("city"); }

  get zipCode():any { return this.userForm.get("address").get("zipCode"); }

  constructor(private usersService: UserData, private router: Router,
    private toasterService: NbToastrService,
    private fb: FormBuilder) { }

  ngOnInit():void {
    this.initUserForm();
  }

  initUserForm():void {
    this.userForm = this.fb.group({
      id: this.fb.control(""),
      role: this.fb.control(""),
      firstName: this.fb.control("", [Validators.minLength(3), Validators.maxLength(20)]),
      lastName: this.fb.control("", [Validators.minLength(3), Validators.maxLength(20)]),
      login: this.fb.control("", [Validators.required, Validators.minLength(6), Validators.maxLength(20)]),
      age: this.fb.control("", [Validators.required, Validators.min(1),
      Validators.max(120), Validators.pattern(NUMBERS_PATTERN)]),
      email: this.fb.control("", [
        Validators.required,
        Validators.pattern(EMAIL_PATTERN),
      ]),
      address: this.fb.group({
        street: this.fb.control(""),
        city: this.fb.control(""),
        zipCode: this.fb.control(""),
      }),
    });
  }

  convertToUser(value: any): User {
    const user: User = value;
    return user;
  }

  save():void {
    const user: User = this.convertToUser(this.userForm.value);
    this.usersService.create(user)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((data) => {
        console.log(data);
        this.handleSuccessResponse();
        this.initUserForm();
      },
        err => {
          this.handleWrongResponse(err);
        });
  }

  handleSuccessResponse():void {
    this.toasterService.success("", `Update success!`);
    this.back();
  }

  handleWrongResponse(err:any):void {
    this.toasterService.danger(err.error.message, `This email has already taken!`);
  }

  back():void {
    this.router.navigate(["/pages"]);
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
