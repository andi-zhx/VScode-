/* eslint-disable no-unused-vars */
import { Component, OnInit, OnDestroy } from "@angular/core";
import { User, UserData } from "../../../@core/interfaces/common/users";
import { takeUntil } from "rxjs/operators";
import { Router, ActivatedRoute } from "@angular/router";
import { NbTokenService } from "@nebular/auth";
import { UserStore } from "../../../@core/stores/user.store";
import { NbToastrService } from "@nebular/theme";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Subject } from "rxjs";
import { NUMBERS_PATTERN, EMAIL_PATTERN } from '../../../@auth/components/constants';

@Component({
  selector: "ngx-user-edit",
  templateUrl: "./user-edit.component.html",
  styleUrls: ["./user-edit.component.scss"]
})
export class UserEditComponent implements OnInit, OnDestroy {
  userForm: FormGroup;
  protected readonly unsubscribe$ = new Subject<void>();

  get firstName(): any { return this.userForm.get("firstName"); }

  get lastName(): any { return this.userForm.get("lastName"); }

  get login(): any { return this.userForm.get("login"); }

  get email(): any { return this.userForm.get("email"); }

  get age(): any { return this.userForm.get("age"); }

  get street(): any { return this.userForm.get("address").get("street"); }

  get city(): any { return this.userForm.get("address").get("city"); }

  get zipCode(): any { return this.userForm.get("address").get("zipCode"); }


  constructor(private usersService: UserData,
    private router: Router,
    private route: ActivatedRoute,
    private toasterService: NbToastrService,
    private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.initUserForm();
    this.loadUserData();
  }
  initUserForm(): void {
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

  loadUserData(): void {
    const id: number = parseInt(this.route.snapshot.paramMap.get("id"), 10);
    if (id) {
      this.loadUser(id);
    }
  }

  loadUser(id?: any): void {
    this.usersService.get(id)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((user) => {
        this.userForm.setValue({
          id: user.id,
          role: user.role ? user.role : "",
          firstName: user.firstName ? user.firstName : "",
          lastName: user.lastName ? user.lastName : "",
          login: user.login ? user.login : "",
          age: user.age ? user.age : "",
          email: user.email,
          address: {
            street: (user.address && user.address.street) ? user.address.street : "",
            city: (user.address && user.address.city) ? user.address.city : "",
            zipCode: (user.address && user.address.zipCode) ? user.address.zipCode : "",
          },
        });
      });
  }
  convertToUser(value: any): User {
    const user: User = value;
    return user;
  }

  save(): void {
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

  handleSuccessResponse(): void {
    this.toasterService.success("", `Update success!`);
    this.back();
  }

  handleWrongResponse(err: any): void {
    this.toasterService.danger(err.error.message, `This email has already taken!`);
  }

  back(): void {
    this.router.navigate(["/pages/users/userstable"]);
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
