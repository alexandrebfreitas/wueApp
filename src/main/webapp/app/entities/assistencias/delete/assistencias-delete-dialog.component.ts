import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAssistencias } from '../assistencias.model';
import { AssistenciasService } from '../service/assistencias.service';

@Component({
  templateUrl: './assistencias-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AssistenciasDeleteDialogComponent {
  assistencias?: IAssistencias;

  protected assistenciasService = inject(AssistenciasService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.assistenciasService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
