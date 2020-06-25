
/* eslint-disable no-unused-vars */
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { Ng2SmartTableModule } from "ng2-smart-table";

import { ThemeModule } from "../../@theme/theme.module";
import { NewemployeemenuRoutingModule } from "./newemployeemenu-routing.module";
import { AuthModule } from "../../@auth/auth.module";

// components
import { NewemployeemenuComponent } from "./newemployeemenu.component";
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

import { NewemployeeComponent } from "./newemployee/newemployee.component";


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
    NewemployeemenuRoutingModule,
    ThemeModule,
    AuthModule,
    Ng2SmartTableModule,
    ComponentsModule,
    ReactiveFormsModule,
    ...NB_MODULES,
  ],
  declarations: [
    NewemployeemenuComponent,
    NewemployeeComponent,
  ],
  entryComponents: [
  ],
  providers: [],
})
export class NewemployeemenuModule { }
