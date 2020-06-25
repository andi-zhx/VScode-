
/* eslint-disable no-unused-vars */
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { MenuData } from "../../../interfaces/common/menu";
import { MenuApi } from "../api/menu.api";

@Injectable()
export class MenuService extends MenuData {

    constructor(private api: MenuApi) {
        super();
    }
    getAllmenu(): Observable<any> {
        return this.api.getAllmenu();
    }
}
