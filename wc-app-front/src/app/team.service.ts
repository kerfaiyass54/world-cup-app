import { Injectable, inject } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private http = inject(HttpClient);

  private api =
    'http://localhost:8010/api/teams';

  getAll() {

    return this.http.get<any>(
      this.api
    );
  }

  getById(
    id: string
  ) {

    return this.http.get<any>(
      `${this.api}/${id}`
    );
  }

  create(
    payload: any
  ) {

    return this.http.post<any>(
      this.api,
      payload
    );
  }

  update(
    id: string,
    payload: any
  ) {

    return this.http.put<any>(
      `${this.api}/${id}`,
      payload
    );
  }

  delete(
    id: string
  ) {

    return this.http.delete<any>(
      `${this.api}/${id}`
    );
  }
}
