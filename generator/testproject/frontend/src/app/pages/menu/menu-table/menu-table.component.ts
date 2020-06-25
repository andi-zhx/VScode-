
/* eslint-disable no-unused-vars */
import { Component, OnInit } from "@angular/core";
import { DataSource } from "ng2-smart-table/lib/lib/data-source/data-source";
import { NbToastrService, NbMenuItem } from "@nebular/theme";
import { Router } from "@angular/router";
import { MenuData } from "../../../@core/interfaces/common/menu";
import { PagesMenu } from '../../pages-menu';
import { takeWhile } from 'rxjs/operators';


@Component({
  selector: "gg-menu-table",
  templateUrl: "./menu-table.component.html",
  styleUrls: ["./menu-table.component.scss"]
})
export class MenuTableComponent implements OnInit {
  menu: NbMenuItem[]= [
    {
      title: "FEATURES",
      group: true,
    }];
    users: { name: string, title: string }[] = [
      { name: 'Carla Espinosa', title: 'Nurse' },
      { name: 'Bob Kelso', title: 'Doctor of Medicine' },
      { name: 'Janitor', title: 'Janitor' },
      { name: 'Perry Cox', title: 'Doctor of Medicine' },
      { name: 'Ben Sullivan', title: 'Carpenter and photographer' },
    ];
  constructor( private toasterService: NbToastrService,private pagesMenu: PagesMenu,
    private router: Router,private menuData: MenuData,) {}

  ngOnInit(): void {
    this.initMenu();
  }
  initMenu():void {
    // this.menuData.getAllmenu().pipe().subscribe(result => {
    //   this.menu = result;
    // });
     this.pagesMenu.getMenu()
      .pipe()
      .subscribe(menu => {
        this.menu=menu;
      });
  }

  handleSuccessResponse(): void {
    this.toasterService.success("", "Data submission successful!");
  }

  handleWrongResponse(err: any): void {
    this.toasterService.danger(err, "Data submission fails!");
  }
}
