/* eslint-disable no-unused-vars */
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../../../../../environments/environment";
import { DataSource } from "ng2-smart-table/lib/lib/data-source/data-source";
import { ServerDataSource } from "ng2-smart-table";

@Injectable()
export class HttpService {
  localsoure: ServerDataSource = null;
  loaduri: string = null;
  get apiUrl(): string {
    return environment.apiUrl;
  }

  constructor(private http: HttpClient) { }

  getServerDataSource(uri: string): DataSource {
    if (this.localsoure === null || this.loaduri === null || this.loaduri !== uri) {
      this.localsoure = new ServerDataSource(this.http,
        {
          endPoint: uri,
          totalKey: "totalCount",
          dataKey: "items",
          pagerPageKey: "pageNumber",
          pagerLimitKey: "pageSize",
          filterFieldKey: "filterBy#field#",
          sortFieldKey: "sortBy",
          sortDirKey: "orderBy",
        });
      this.loaduri = uri;
    }
    return this.localsoure;
  }

  get(endpoint: string, options?: any): Observable<any> {
    return this.http.get(`${this.apiUrl}/${endpoint}`, options);
  }

  post(endpoint: string, data: any, options?: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/${endpoint}`, data, options);
  }

  put(endpoint: string, data: any, options?: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${endpoint}`, data, options);
  }

  delete(endpoint: string, options?: any): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${endpoint}`, options);
  }

  utilgetFilterts(filters: any, sort: any): string {
    var fiter_ts: string = "";
    let array: Array<string> = [];
    if (filters.length > 0 || sort.length > 0) {
      for (const s of sort) {
        array.push("sortBy=" + s.field + "&orderBy=" + s.direction);
      }
      for (const filter of filters) {
        array.push("filterBy" + filter.field + "=" + filter.search);
        fiter_ts = fiter_ts + "filterBy" + filter.field + "=" + filter.search;
      }
    }
    if (array.length > 0) {
      fiter_ts = "?" + array.join("&");
    }
    return fiter_ts;
  }
}
