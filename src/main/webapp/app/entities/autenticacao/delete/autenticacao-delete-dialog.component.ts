import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAutenticacao } from '../autenticacao.model';
import { AutenticacaoService } from '../service/autenticacao.service';

@Component({
  templateUrl: './autenticacao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AutenticacaoDeleteDialogComponent {
  autenticacao?: IAutenticacao;

  protected autenticacaoService = inject(AutenticacaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.autenticacaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
