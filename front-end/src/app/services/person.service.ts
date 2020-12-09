import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { Person } from '../model/Person';

const baseUrl = 'http://localhost:8080/api/person';

@Injectable({
    providedIn: 'root'
})
export class PersonService {

    constructor(private http: HttpClient) { }

    createAuthorizationHeader(httpHeaders: HttpHeaders) {
        httpHeaders = httpHeaders.append('Authorization', 'Basic ' + btoa('mohshad:password'));
        httpHeaders = httpHeaders.append('Accept', 'application/json');
        return httpHeaders;
    }

    getAll(): Observable<Person[]> {
        let httpHeaders: HttpHeaders = new HttpHeaders();
        return this.http.get<Person[]>(baseUrl+"?page=0&size=100", {
            headers: this.createAuthorizationHeader(httpHeaders)
        });
    }

    get(id): Observable<Person> {
        let httpHeaders: HttpHeaders = new HttpHeaders();
        return this.http.get<Person>(`${baseUrl}/getById/${id}`, {
            headers: this.createAuthorizationHeader(httpHeaders)
        });
    }

    create(data): Observable<any> {
        let httpHeaders: HttpHeaders = new HttpHeaders();
        return this.http.post(baseUrl, data, {
            headers: this.createAuthorizationHeader(httpHeaders)
        });
    }

    update(id, data): Observable<any> {
        let httpHeaders: HttpHeaders = new HttpHeaders();
        return this.http.put(`${baseUrl}/${id}`, data, {
            headers: this.createAuthorizationHeader(httpHeaders)
        });
    }

    delete(id): Observable<any> {
        let httpHeaders: HttpHeaders = new HttpHeaders();
        return this.http.delete(`${baseUrl}/${id}`, {
            headers: this.createAuthorizationHeader(httpHeaders)
        });
    }

}