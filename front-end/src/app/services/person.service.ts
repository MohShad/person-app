import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { Person } from '../model/Person';

const baseUrl = 'http://localhost:8080/api/v1/person';

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
        return this.http.get<Person[]>(baseUrl + "?page=0&size=100", {
            headers: this.createAuthorizationHeader(httpHeaders)
        });
    }

    get(id: any): Observable<Person> {
        let httpHeaders: HttpHeaders = new HttpHeaders();
        return this.http.get<Person>(`${baseUrl}/getById/${id}`, {
            headers: this.createAuthorizationHeader(httpHeaders)
        });
    }

    getByCpf(cpf: any): Observable<Person> {
        let httpHeaders: HttpHeaders = new HttpHeaders();
        return this.http.get<Person>(`${baseUrl}/getByCpf/${cpf}`, {
            headers: this.createAuthorizationHeader(httpHeaders)
        });
    }

    existCpf(cpf: any): Observable<any> {
        let httpHeaders: HttpHeaders = new HttpHeaders();
        return this.http.get<any>(`${baseUrl}/existCpf/${cpf}`, {
            headers: this.createAuthorizationHeader(httpHeaders)
        });
    }

    create(data: any): Observable<any> {
        let httpHeaders: HttpHeaders = new HttpHeaders();
        return this.http.post(baseUrl, data, {
            headers: this.createAuthorizationHeader(httpHeaders)
        });
    }

    update(id: any, data: any): Observable<any> {
        let httpHeaders: HttpHeaders = new HttpHeaders();
        return this.http.put(`${baseUrl}/${id}`, data, {
            headers: this.createAuthorizationHeader(httpHeaders)
        });
    }

    delete(id: any): Observable<any> {
        let httpHeaders: HttpHeaders = new HttpHeaders();
        return this.http.delete(`${baseUrl}/${id}`, {
            headers: this.createAuthorizationHeader(httpHeaders)
        });
    }

}
