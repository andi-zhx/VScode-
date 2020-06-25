
/* eslint-disable no-unused-vars */
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { Ng2SmartTableModule } from "ng2-smart-table";

import { ThemeModule } from "../../@theme/theme.module";
import { CurretemployeemenuRoutingModule } from "./curretemployeemenu-routing.module";
import { AuthModule } from "../../@auth/auth.module";

// components
import { CurretemployeemenuComponent } from "./curretemployeemenu.component";
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

import { EmployeeeditComponent } from "./employeeedit/employeeedit.component";


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
    CurretemployeemenuRoutingModule,
    ThemeModule,
    AuthModule,
    Ng2SmartTableModule,
    ComponentsModule,
    ReactiveFormsModule,
    ...NB_MODULES,
  ],
  declarations: [
    CurretemployeemenuComponent,
    EmployeeeditComponent,
  ],
  entryComponents: [
  ],
  providers: [],
})
export class CurretemployeemenuModule { }
