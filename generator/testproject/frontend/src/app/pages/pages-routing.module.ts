
/* eslint-disable no-unused-vars */
import { RouterModule, Routes } from "@angular/router";
import { NgModule } from "@angular/core";

import { PagesComponent } from "./pages.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { ECommerceComponent } from "./e-commerce/e-commerce.component";
import { NotFoundComponent } from "./miscellaneous/not-found/not-found.component";

const routes: Routes = [{
  path: "",
  component: PagesComponent,
  children: [
    {
        path: "gg-employeelistmenu",
        loadChildren: () => import("./gg-employeelistmenu/employeelistmenu.module")
            .then(m => m.EmployeelistmenuModule),
    },
    {
        path: "gg-newemployeemenu",
        loadChildren: () => import("./gg-newemployeemenu/newemployeemenu.module")
            .then(m => m.NewemployeemenuModule),
    },
    {
        path: "gg-curretemployeemenu",
        loadChildren: () => import("./gg-curretemployeemenu/curretemployeemenu.module")
            .then(m => m.CurretemployeemenuModule),
    },
    {
      path: "dashboard",
      component: ECommerceComponent,
    },
    {
      path: "iot-dashboard",
      component: DashboardComponent,
    },
    {
      path: "users",
      loadChildren: () => import("./users/users.module")
        .then(m => m.UsersModule),
    },
    {
      path: "menu",
      loadChildren: () => import("./menu/menu.module")
        .then(m => m.MenuModule),
    },
    {
      path: "roles",
      loadChildren: () => import("./roles/roles.module")
        .then(m => m.RolesModule),
    },
    {
      path: "layout",
      loadChildren: () => import("./layout/layout.module")
        .then(m => m.LayoutModule),
    },
    {
      path: "forms",
      loadChildren: () => import("./forms/forms.module")
        .then(m => m.FormsModule),
    },
    {
      path: "ui-features",
      loadChildren: () => import("./ui-features/ui-features.module")
        .then(m => m.UiFeaturesModule),
    },
    {
      path: "modal-overlays",
      loadChildren: () => import("./modal-overlays/modal-overlays.module")
        .then(m => m.ModalOverlaysModule),
    },
    {
      path: "extra-components",
      loadChildren: () => import("./extra-components/extra-components.module")
        .then(m => m.ExtraComponentsModule),
    },
    {
      path: "maps",
      loadChildren: () => import("./maps/maps.module")
        .then(m => m.MapsModule),
    },
    {
      path: "charts",
      loadChildren: () => import("./charts/charts.module")
        .then(m => m.ChartsModule),
    },
    {
      path: "editors",
      loadChildren: () => import("./editors/editors.module")
        .then(m => m.EditorsModule),
    },
    {
      path: "tables",
      loadChildren: () => import("./tables/tables.module")
        .then(m => m.TablesModule),
    },
    {
      path: "miscellaneous",
      loadChildren: () => import("./miscellaneous/miscellaneous.module")
        .then(m => m.MiscellaneousModule),
    },
    {
      path: "",
      redirectTo: "dashboard",
      pathMatch: "full",
    },
    {
      path: "**",
      component: NotFoundComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
