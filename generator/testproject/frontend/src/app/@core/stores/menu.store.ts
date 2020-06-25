
/* eslint-disable no-unused-vars */
import { Injectable } from "@angular/core";
import { Menu } from "../interfaces/common/menu";

@Injectable({
    providedIn: "root",
})
export class MenuStore {
    private item: Menu;

    // tslint:disable-next-line: no-empty
    constructor() {}

    getGgMenu(): Menu {
        return this.item;
    }

    setGgMenu(paramUser: Menu): void {
        this.item = paramUser;
    }
}
