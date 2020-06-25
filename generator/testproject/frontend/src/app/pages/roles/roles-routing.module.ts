
/* eslint-disable no-unused-vars */
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { RolesComponent } from "./roles.component";

import { AdminGuard } from "../../@auth/admin.guard";
import { RolesTableeditbuttonComponent } from "./roles-table-editbutton/roles-table-editbutton.component";
import { RolesTableComponent } from "./roles-table/roles-table.component";


const routes: Routes = [{
  path: "",
  component: RolesComponent,
  children: [
    {
        path: "roles-table-editbutton/:id",
        component: RolesTableeditbuttonComponent,
    },
    {
      path: "rolestable",
      component: RolesTableComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RolesRoutingModule {

}
