import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IApidiaexcepcional } from '../apidiaexcepcional.model';
import { ApidiaexcepcionalService } from '../service/apidiaexcepcional.service';

@Component({
  templateUrl: './apidiaexcepcional-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ApidiaexcepcionalDeleteDialogComponent {
  apidiaexcepcional?: IApidiaexcepcional;

  protected apidiaexcepcionalService = inject(ApidiaexcepcionalService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.apidiaexcepcionalService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
