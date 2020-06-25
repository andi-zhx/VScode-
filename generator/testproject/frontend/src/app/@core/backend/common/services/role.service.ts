
/* eslint-disable no-unused-vars */
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { DataSource } from "ng2-smart-table/lib/lib/data-source/data-source";
import { NbAuthService } from "@nebular/auth";
import { RoleData, Role } from "../../../interfaces/common/role";
import { RoleApi } from "../api/role.api";

@Injectable()
export class RoleService extends RoleData {
    get DataSourceroles(): DataSource {
        return this.api.DataSourceroles;
    }
    constructor(private api: RoleApi) {
        super();
    }
    createroles(user: any): Observable<Role> {
        return this.api.createroles(user);
      }
    
    updateroles(item: any): Observable<any> {
        return this.api.updateroles(item);
    }
    getroles(id: number): Observable<any> {
        return this.api.getroles(id);
    }
    deleteroles(id: number): Observable<boolean> {
        return this.api.deleteroles(id);
    }
    exportroles(filters: any, sort: any): Observable<any> {
        return this.api.exportroles(filters, sort);
    }

}
