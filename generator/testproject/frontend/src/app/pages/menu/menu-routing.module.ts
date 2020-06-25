
/* eslint-disable no-unused-vars */
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { MenuComponent } from "./menu.component";
import { MenuTableComponent } from "./menu-table/menu-table.component";


const routes: Routes = [{
  path: "",
  component: MenuComponent,
  children: [
    {
      path: "menutable",
      component: MenuTableComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MenuRoutingModule {

}
