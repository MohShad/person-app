import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';

import { PersonService } from '../../services/person.service'

@Component({
    selector: 'app-person-list',
    templateUrl: './person-list.component.html',
    styleUrls: ['./person-list.component.css']
})
export class PersonListComponent implements OnInit {

    persons: any = [];
    currentPerson: any;
    currentIndex = -1;
    cpf: any;

    constructor(private personService: PersonService) { }

    ngOnInit(): void {
        this.retrievePersons();
    }

    retrievePersons(): void {
        this.personService.getAll()
            .subscribe(
                data => {
                    this.persons = data;
                },
                error => {
                    console.log(error);
                });
    }

    refreshList(): void {
        this.retrievePersons();
        this.currentPerson = null;
        this.currentIndex = -1;
    }

    setActivePerson(person: Object, index: number): void {
        this.currentPerson = person;
        let myDate = moment(this.currentPerson.dataNascimento).format("MM/DD/YYYY");
        this.currentPerson.dataNascimento = myDate;
        this.currentIndex = index;
    }

    search(): void {
        this.personService.getByCpf(this.cpf)
            .subscribe(
                data => {
                    let myDate = moment(data.dataNascimento).format("MM/DD/YYYY");
                    this.currentPerson = data;
                    this.currentPerson.dataNascimento = myDate;
                    console.log(data);
                },
                error => {
                    console.log(error);
                });
    }

}
