import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import * as moment from 'moment';

import { PersonService } from '../../services/person.service'
import { AlertService } from '../../services/alert.service'

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

    systemDate = moment(new Date()).format('YYYY-MM-DD');

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
        private personService: PersonService,
        private alertService: AlertService) {
    }

    ngOnInit(): void {
    }

    get f() {
        return this.personForm.controls;
    }

    async submit() {
        let isValidCpf: any;
        const myDate = moment(this.personForm.controls.person_dataNascimento.value).format('YYYY-MM-DD');
        if (this.personForm.status === 'VALID') {
            await this.personService.existCpf(this.personForm.controls.person_cpf.value)
                .subscribe(
                    response => {
                        isValidCpf = response.success;
                        if (myDate < this.systemDate) {
                            const date = moment(myDate, 'YYYY-MM-DD 00:00:00');
                            if (!isValidCpf) {
                                const data = {
                                    nome: this.personForm.controls.person_nome.value,
                                    sexo: this.personForm.controls.person_sexo.value,
                                    email: this.personForm.controls.person_email.value,
                                    dataNascimento: date,
                                    naturalidade: this.personForm.controls.person_naturalidade.value,
                                    nacionalidade: this.personForm.controls.person_nacionalidade.value,
                                    cpf: this.personForm.controls.person_cpf.value,
                                };

                                this.personService.create(data)
                                    .subscribe(
                                        response => {
                                            console.log(response);
                                            this.router.navigate(['/persons']);
                                            this.alertService.showSuccessAlert('Sucesso!', 'A pessoa foi cadastrado com sucesso!');
                                        },
                                        error => {
                                            console.log(error);
                                        });
                            } else {
                                this.alertService.showInfoAlert('CPF Duplicado;', 'Informe outro CPF!');
                                this.person.cpf = '';
                            }
                        } else {
                            this.alertService.showInfoAlert('Data inválido!', 'A data de nascimento deve ser menor que data atual!');
                            this.person.dataNascimento = '';
                        }

                    },
                    error => {
                        console.log(error);
                    });
        } else {
            this.alertService.showWarningAlert('Erro', 'Deve preencher Todos os campos obrigatórios!')
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
