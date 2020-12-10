import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import * as moment from 'moment';
import Swal from 'sweetalert2';

import { PersonService } from '../../services/person.service';
import { AlertService } from '../../services/alert.service';

@Component({
    selector: 'app-person-details',
    templateUrl: './person-details.component.html',
    styleUrls: ['./person-details.component.css']
})
export class PersonDetailsComponent implements OnInit {

    currentPerson: any = {};
    systemDate = moment(new Date(), 'YYYY-MM-DD 00:00:00');

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
        private personService: PersonService,
        private alertService: AlertService,
        private route: ActivatedRoute,
        private router: Router) { }

    ngOnInit(): void {
        this.getPerson(this.route.snapshot.paramMap.get('id'));
    }

    get f() {
        return this.personForm.controls;
    }

    submit() {

        const myDate = moment(this.personForm.controls.person_dataNascimento.value, 'YYYY-MM-DD');

        Swal.fire({
            title: 'Deseja alterar?',
            text: 'Deseja alterar o registro da pessoa?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sim!',
            cancelButtonText: 'Não',
        }).then((result) => {

            if (result.isConfirmed) {

                if (this.personForm.status === 'VALID') {
                    if (myDate.day() < this.systemDate.day()) {
                        const data = {
                            nome: this.personForm.controls.person_nome.value,
                            sexo: this.personForm.controls.person_sexo.value,
                            email: this.personForm.controls.person_email.value,
                            dataNascimento: myDate,
                            naturalidade: this.personForm.controls.person_naturalidade.value,
                            nacionalidade: this.personForm.controls.person_nacionalidade.value,
                            cpf: this.personForm.controls.person_cpf.value,
                        };

                        this.personService.update(this.currentPerson.id, data)
                            .subscribe(
                                response => {
                                    this.router.navigate(['/persons']);
                                },
                                error => {
                                    console.log(error);
                                });
                    } else {
                        this.alertService.showInfoAlert('Data inválido!', 'A data de nascimento deve ser menor que data atual!');
                        this.currentPerson.dataNascimento = '';
                    }
                } else {
                    this.alertService.showWarningAlert('Erro', 'Deve preencher Todos os campos obrigatórios!')
                }
            } else if (result.isDismissed) {
                console.log('Clicked No, File is safe!');
            }
        })
    }

    getPerson(id: any): void {
        this.personService.get(id)
            .subscribe(
                data => {
                    this.currentPerson = data;
                    let myDate = moment(this.currentPerson.dataNascimento).format('YYYY-MM-DD');
                    this.currentPerson.dataNascimento = myDate;
                    console.log(data);
                },
                error => {
                    console.log(error);
                });
    }

    deletePerson(): void {

        Swal.fire({
            title: 'Deseja excluir?',
            text: 'Você não será capaz de recuperar este registro!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sim!',
            cancelButtonText: 'Não',
        }).then((result) => {

            if (result.isConfirmed) {
                this.personService.delete(this.currentPerson.id)
                    .subscribe(
                        response => {
                            console.log(response);
                            this.router.navigate(['/persons']);
                        },
                        error => {
                            console.log(error);
                        });
            } else if (result.isDismissed) {
                console.log('Clicked No, File is safe!');
            }
        })

    }
}

