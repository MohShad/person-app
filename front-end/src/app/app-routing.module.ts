import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PersonListComponent } from './components/person-list/person-list.component';
import { PersonDetailsComponent } from './components/person-details/person-details.component';
import { PersonAddComponent } from './components/person-add/person-add.component';

const routes: Routes = [
    { path: '', redirectTo: 'persons', pathMatch: 'full' },
    { path: 'persons', component: PersonListComponent },
    { path: 'person/:id', component: PersonDetailsComponent },
    { path: 'add', component: PersonAddComponent }];

    @NgModule({
    imports: [
        RouterModule.forRoot(routes)
    ],
    exports: [RouterModule]
})
export class AppRoutingModule { }
