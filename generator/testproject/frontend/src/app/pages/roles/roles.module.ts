
/* eslint-disable no-unused-vars */
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { Ng2SmartTableModule } from "ng2-smart-table";

import { ThemeModule } from "../../@theme/theme.module";
import { RolesRoutingModule } from "./roles-routing.module";
import { AuthModule } from "../../@auth/auth.module";

// components
import { RolesComponent } from "./roles.component";
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

import { RolesTableeditbuttonComponent } from "./roles-table-editbutton/roles-table-editbutton.component";
import { RolesTableComponent } from "./roles-table/roles-table.component";


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
    RolesRoutingModule,
    ThemeModule,
    AuthModule,
    Ng2SmartTableModule,
    ComponentsModule,
    ReactiveFormsModule,
    ...NB_MODULES,
  ],
  declarations: [
    RolesComponent,
    RolesTableeditbuttonComponent,
    RolesTableComponent,
  ],
  entryComponents: [
  ],
  providers: [],
})
export class RolesModule { }
