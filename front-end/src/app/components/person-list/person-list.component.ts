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
    currentPerson : any;
    currentIndex = -1;
    nome = '';

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

    setActivePerson(person, index): void {
        this.currentPerson = person;
        let myDate: Date = moment(this.currentPerson.dataNascimento).format("DD/MM/YYYY");
        this.currentPerson.dataNascimento = myDate;
        this.currentIndex = index;
    }

}
