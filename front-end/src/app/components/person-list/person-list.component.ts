import { Component, OnInit } from '@angular/core';
import { PersonService } from '../../services/person.service'

@Component({
    selector: 'app-person-list',
    templateUrl: './person-list.component.html',
    styleUrls: ['./person-list.component.css']
})
export class PersonListComponent implements OnInit {

    persons: any = [];
    currentPerson = null;
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
                    debugger
                    this.persons = data;
                },
                error => {
                    console.log(error);
                });
    }

    refreshList(): void {
        this.retrievePersons();
        debugger
        this.currentPerson = null;
        this.currentIndex = -1;
    }

    setActivePerson(person, index): void {
        debugger
        this.currentPerson = person;
        this.currentIndex = index;
    }

}
