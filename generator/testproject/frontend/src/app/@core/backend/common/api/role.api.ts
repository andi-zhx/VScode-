
/* eslint-disable no-unused-vars */
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { DataSource } from "ng2-smart-table/lib/lib/data-source/data-source";
import { HttpService } from "./http.service";

@Injectable()
export class RoleApi {
    private readonly apiController: string = "role";

    constructor(private api: HttpService) {
    }
    createroles(item: any): Observable<any> {
        return this.api.post(this.apiController, item);
    }
    updateroles(item: any): Observable<any> {
        return this.api.put(this.apiController + "/edit" + "/" + item.id, item);
    }
    getroles(id: number): Observable<any> {
        return this.api.get(this.apiController + "/get" + "/" + id)
            .pipe(map(data => {
                return { ...data };
            }));
    }
    deleteroles(id: number): Observable<boolean> {
        return this.api.delete(this.apiController + "/delete" + "/" + id);
    }
    exportroles(filters: any, sort: any): Observable<any> {
        var fiter_ts: string = this.api.utilgetFilterts(filters, sort);
        return this.api.get(this.apiController + "/export" + fiter_ts, { responseType: "blob", observe: "response" })
            .pipe(data => {
                return data;
            });
    }
    get DataSourceroles(): DataSource {
        return this.api.getServerDataSource(this.api.apiUrl + "/" + this.apiController);
    }
}
