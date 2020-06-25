/* eslint-disable no-unused-vars */
import { Observable } from "rxjs";

export interface Menu {
    id?: number;
    sort?: number;
    name?: string;
    title?: string;
    icon?: string;
    link?: string;
    isOuterLink?: boolean;
    permission?: string;
    hidden?: boolean;
    createtime?: Date;
    type?: string;
    isDefault?: boolean;
    parentname?: string;
    isHome?: boolean;
    isGroup?: boolean;
    isDelete?: boolean;
}

// export interface GGMenuItem {
//     title: string;
//     link?: string;
//     url?: string;
//     icon?: string;
//     expanded?: boolean;
//     children?: GGMenuItem[];
//     target?: string;
//     hidden?: boolean;
//     group?: boolean;
// }
export abstract class MenuData {
    abstract getAllmenu(): Observable<any>;
}