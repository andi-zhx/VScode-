
/* eslint-disable no-unused-vars */
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { HttpService } from "./http.service";
import { Menu } from "../../../interfaces/common/menu";
import { NbMenuItem } from "@nebular/theme";

@Injectable()
export class MenuApi {
    private readonly apiController: string = "menu";

    constructor(private api: HttpService) {
    }
    getAllmenu(): Observable<NbMenuItem[]> {
        var fin: NbMenuItem[] = [];
        return this.api.get(this.apiController + "/getmenu")
            .pipe(map(data => {
                data = this.convertToMenulist(data);
                let catalogdata: Menu[] = [];
                data.forEach(e => {
                    if (e.type === "catalog") {
                        catalogdata.push(e);
                    }
                });
                console.log(catalogdata);
                for (let value of catalogdata) {
                    let re: Menu = this.convertToMenu(value);
                    let child: NbMenuItem[] = [];
                    let childdata: Menu[] = [];
                    data.forEach(e => {
                        if (e.type === "menu" && e.parentname === re.name) {
                            childdata.push(e);
                        }
                    });
                    for (const value of childdata) {
                        let cm: Menu = this.convertToMenu(value);
                        child.push({
                            title: cm.title,
                            group: cm.isGroup === null ? false : cm.isGroup,
                            icon: cm.icon,
                            link: cm.link,
                        });
                    }
                    var menu: NbMenuItem = {
                        title: re.title,
                        group: re.isGroup === null ? false : re.isGroup,
                        icon: re.icon,
                        link: re.link,
                        children: child,
                    };
                    fin.push(menu);
                }
                return fin;
            }));
    }
    convertToMenu(value: any): Menu {
        const data: Menu = value;
        return data;
    }
    convertToMenulist(value: any): Menu[] {
        const data: Menu[] = value;
        return data;
    }
}
