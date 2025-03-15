import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IAutenticacao } from '../autenticacao.model';

@Component({
  selector: 'jhi-autenticacao-detail',
  templateUrl: './autenticacao-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class AutenticacaoDetailComponent {
  autenticacao = input<IAutenticacao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
