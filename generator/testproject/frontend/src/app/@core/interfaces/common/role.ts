
/* eslint-disable no-unused-vars */
import { Observable } from "rxjs";
import { DataSource } from "ng2-smart-table/lib/lib/data-source/data-source";
import { DecimalPipe } from "@angular/common";

export interface Role {
    id?: number;
    isDefault?: boolean;
    name?: string;
}
export abstract class RoleData {
    abstract updateroles(item: any): Observable<any>;
    abstract getroles(id: number): Observable<any>;
    abstract deleteroles(id: number): Observable<boolean>;
    abstract exportroles(filters: any,sort:any): Observable<any>;
    abstract createroles(role: Role): Observable<Role>;
    abstract get DataSourceroles(): DataSource;
}
