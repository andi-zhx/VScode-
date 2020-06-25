/* eslint-disable no-unused-vars */

import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { UsersComponent } from "./users.component";
import { UserComponent } from "./user/user.component";

import { AdminGuard } from "../../@auth/admin.guard";
import { UsersTableComponent } from "./users-table/users-table.component";
import { UserNewComponent } from "./user-new/user-new.component";
import { UserEditComponent } from "./user-edit/user-edit.component";

const routes: Routes = [{
  path: "",
  component: UsersComponent,
  children: [
    {
      path: "edit/:id",
      canActivate: [AdminGuard],
      component: UserComponent,
    },
    {
      path: "current",
      component: UserComponent,
    },
    {
      path: "add",
      canActivate: [AdminGuard],
      component: UserComponent,
    },
    {
      path: "userstable",
      component: UsersTableComponent,
    },
    {
      path: "usernew",
      component: UserNewComponent,
    },
    {
      path: "useredit/:id",
      component: UserEditComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UsersRoutingModule {

}
