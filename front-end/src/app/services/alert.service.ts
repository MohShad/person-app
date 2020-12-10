import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
    providedIn: 'root'
})
export class AlertService {

    constructor() { }


    showSuccessAlert(title: string, message: string) {
        Swal.fire(title, message, 'success');
    }

    showInfoAlert(title: string, message: string) {
        Swal.fire(title, message, 'info');
    }

    showWarningAlert(title: string, message: string) {
        Swal.fire(title, message, 'warning');
    }

    handleWarningAlert() {

        Swal.fire({
            title: 'Deseja excluir?',
            text: 'Você não será capaz de recuperar este registro!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sim!',
            cancelButtonText: 'Não',
        }).then((result) => {

            if (result.isConfirmed) {

                console.log('Clicked Yes, File deleted!');

            } else if (result.isDismissed) {

                console.log('Clicked No, File is safe!');

            }
        })

    }
}
