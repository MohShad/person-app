import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgxMaskModule, IConfig } from 'ngx-mask';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PersonListComponent } from './components/person-list/person-list.component';
import { PersonAddComponent } from './components/person-add/person-add.component';
import { PersonDetailsComponent } from './components/person-details/person-details.component';


const maskConfigFunction: () => Partial<IConfig> = () => {
    return {
        validation: false,
    };
};

@NgModule({
    declarations: [
        AppComponent,
        PersonListComponent,
        PersonAddComponent,
        PersonDetailsComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        FormsModule,
        HttpClientModule,
        NgxMaskModule.forRoot(maskConfigFunction),
    ],
    providers: [
        FormBuilder
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
