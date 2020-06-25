/* eslint-disable no-unused-vars */
import { Component, OnDestroy } from "@angular/core";
import { takeWhile } from "rxjs/operators";
import { NbTokenService } from "@nebular/auth";
import { NbMenuItem, NbMenuService } from "@nebular/theme";
import { PagesMenu } from "./pages-menu";
import { InitUserService } from "../@theme/services/init-user.service";
import { MenuData } from "../@core/interfaces/common/menu";

@Component({
  selector: "ngx-pages",
  styleUrls: ["pages.component.scss"],
  template: `
    <ngx-one-column-layout>
      <nb-menu [items]="menu"></nb-menu>
      <router-outlet></router-outlet>
    </ngx-one-column-layout>
  `,
})
export class PagesComponent implements OnDestroy {

  menu: NbMenuItem[]=[
    {
      title: "GG-menu",
      group: true,
    }];
  alive: boolean = true;

  constructor(private pagesMenu: PagesMenu,
    private tokenService: NbTokenService,
    protected initUserService: InitUserService,private menuData: MenuData
  ) {
    this.initMenu();

    this.tokenService.tokenChange()
      .pipe(takeWhile(() => this.alive))
      .subscribe(() => {
        this.initMenu();
      });
  }

  initMenu():void {
    this.menuData.getAllmenu().pipe(takeWhile(() => this.alive)).subscribe(result => {
      this.menu = result;
    });
    // this.pagesMenu.getMenu()
    //   .pipe(takeWhile(() => this.alive))
    //   .subscribe(menu => {
    //     console.log(menu);
    //   });
  }

  ngOnDestroy(): void {
    this.alive = false;
  }
}
