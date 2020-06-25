
/* eslint-disable no-unused-vars */
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { Ng2SmartTableModule } from "ng2-smart-table";

import { ThemeModule } from "../../@theme/theme.module";
import { EmployeelistmenuRoutingModule } from "./employeelistmenu-routing.module";
import { AuthModule } from "../../@auth/auth.module";

// components
import { EmployeelistmenuComponent } from "./employeelistmenu.component";
import { ComponentsModule } from "../../@components/components.module";
// components

import {
  NbActionsModule,
  NbButtonModule,
  NbCardModule,
  NbInputModule,
  NbTabsetModule,
  NbUserModule,
  NbRadioModule,
  NbSelectModule,
  NbListModule,
  NbIconModule,
  NbSpinnerModule,
  NbDatepickerModule,
  NbCheckboxModule,
} from "@nebular/theme";

import { EmployeelistComponent } from "./employeelist/employeelist.component";
import { EmployeemaintaineditbuttonComponent } from "./employeemaintain-editbutton/employeemaintain-editbutton.component";
import { EmployeemaintainComponent } from "./employeemaintain/employeemaintain.component";


const  NB_MODULES:any = [
  NbActionsModule,
  NbButtonModule,
  NbCardModule,
  NbInputModule,
  NbTabsetModule,
  NbUserModule,
  NbRadioModule,
  NbSelectModule,
  NbListModule,
  NbIconModule,
  NbSpinnerModule,
  NbDatepickerModule,
  NbInputModule,
  NbCheckboxModule,
];

@NgModule({
  imports: [
    EmployeelistmenuRoutingModule,
    ThemeModule,
    AuthModule,
    Ng2SmartTableModule,
    ComponentsModule,
    ReactiveFormsModule,
    ...NB_MODULES,
  ],
  declarations: [
    EmployeelistmenuComponent,
    EmployeelistComponent,
    EmployeemaintaineditbuttonComponent,
    EmployeemaintainComponent,
  ],
  entryComponents: [
  ],
  providers: [],
})
export class EmployeelistmenuModule { }
