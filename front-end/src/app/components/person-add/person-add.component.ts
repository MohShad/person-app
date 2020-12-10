import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import * as moment from 'moment';

import { PersonService } from '../../services/person.service'
@Component({
    selector: 'app-person-add',
    templateUrl: './person-add.component.html',
    styleUrls: ['./person-add.component.css']
})
export class PersonAddComponent implements OnInit {

    person = {
        nome: '',
        sexo: '',
        email: '',
        dataNascimento: '',
        naturalidade: '',
        nacionalidade: '',
        cpf: '',
    };

    personForm = new FormGroup({
        person_nome: new FormControl('', [Validators.required, Validators.minLength(3)]),
        person_sexo: new FormControl(''),
        person_email: new FormControl('', Validators.email),
        person_dataNascimento: new FormControl('', Validators.required),
        person_naturalidade: new FormControl(''),
        person_nacionalidade: new FormControl(''),
        person_cpf: new FormControl('', Validators.required),
    });

    constructor(
        private router: Router,
        private personService: PersonService) {
    }

    ngOnInit(): void {
    }

    get f() {
        return this.personForm.controls;
    }

    submit() {
        if (this.personForm.status === 'VALID') {
            const myDate = moment(this.personForm.controls.person_dataNascimento.value, 'YYYY-MM-DD');
            const data = {
                nome: this.personForm.controls.person_nome.value,
                sexo: this.personForm.controls.person_sexo.value,
                email: this.personForm.controls.person_email.value,
                dataNascimento: myDate,
                naturalidade: this.personForm.controls.person_naturalidade.value,
                nacionalidade: this.personForm.controls.person_nacionalidade.value,
                cpf: this.personForm.controls.person_cpf.value,
            };

            this.personService.create(data)
                .subscribe(
                    response => {
                        console.log(response);
                        this.router.navigate(['/persons']);
                    },
                    error => {
                        console.log(error);
                    });
        }
    }

    resetValue() {
        this.personForm.reset(
            {
                name: '',
                sexo: '',
                email: '',
                dataNascimento: '',
                naturalidade: '',
                nacionalidade: '',
                cpf: ''
            }
        );
    }
}
