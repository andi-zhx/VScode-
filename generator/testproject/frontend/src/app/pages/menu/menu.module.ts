
/* eslint-disable no-unused-vars */
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { Ng2SmartTableModule } from "ng2-smart-table";

import { ThemeModule } from "../../@theme/theme.module";
import { MenuRoutingModule } from "./menu-routing.module";
import { AuthModule } from "../../@auth/auth.module";

// components
import { MenuComponent } from "./menu.component";
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
  NbMenuModule,
  NbLayoutModule,
  NbSidebarModule,
} from "@nebular/theme";

import { MenuTableComponent } from "./menu-table/menu-table.component";


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
  NbMenuModule,NbLayoutModule,
];

@NgModule({
  imports: [
    MenuRoutingModule,
    ThemeModule,
    AuthModule,
    Ng2SmartTableModule,
    ComponentsModule,
    ReactiveFormsModule,
    NbSidebarModule,
    // NbSidebarModule.forRoot(),
    ...NB_MODULES,
  ],
  declarations: [
    MenuComponent,
    MenuTableComponent,
  ],
  entryComponents: [
  ],
  providers: [],
})
export class MenuModule { }
