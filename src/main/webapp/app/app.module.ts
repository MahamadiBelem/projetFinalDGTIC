import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ProjetDgticSharedModule } from 'app/shared/shared.module';
import { ProjetDgticCoreModule } from 'app/core/core.module';
import { ProjetDgticAppRoutingModule } from './app-routing.module';
import { ProjetDgticHomeModule } from './home/home.module';
import { ProjetDgticEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    ProjetDgticSharedModule,
    ProjetDgticCoreModule,
    ProjetDgticHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ProjetDgticEntityModule,
    ProjetDgticAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class ProjetDgticAppModule {}
