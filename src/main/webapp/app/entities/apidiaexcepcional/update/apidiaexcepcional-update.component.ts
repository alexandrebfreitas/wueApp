import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ApidiaexcepcionalService } from '../service/apidiaexcepcional.service';
import { IApidiaexcepcional } from '../apidiaexcepcional.model';
import { ApidiaexcepcionalFormGroup, ApidiaexcepcionalFormService } from './apidiaexcepcional-form.service';

@Component({
  selector: 'jhi-apidiaexcepcional-update',
  templateUrl: './apidiaexcepcional-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ApidiaexcepcionalUpdateComponent implements OnInit {
  isSaving = false;
  apidiaexcepcional: IApidiaexcepcional | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected apidiaexcepcionalService = inject(ApidiaexcepcionalService);
  protected apidiaexcepcionalFormService = inject(ApidiaexcepcionalFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ApidiaexcepcionalFormGroup = this.apidiaexcepcionalFormService.createApidiaexcepcionalFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apidiaexcepcional }) => {
      this.apidiaexcepcional = apidiaexcepcional;
      if (apidiaexcepcional) {
        this.updateForm(apidiaexcepcional);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('wattsUpEnergyApp.error', { ...err, key: `error.file.${err.key}` })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const apidiaexcepcional = this.apidiaexcepcionalFormService.getApidiaexcepcional(this.editForm);
    if (apidiaexcepcional.id !== null) {
      this.subscribeToSaveResponse(this.apidiaexcepcionalService.update(apidiaexcepcional));
    } else {
      this.subscribeToSaveResponse(this.apidiaexcepcionalService.create(apidiaexcepcional));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApidiaexcepcional>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(apidiaexcepcional: IApidiaexcepcional): void {
    this.apidiaexcepcional = apidiaexcepcional;
    this.apidiaexcepcionalFormService.resetForm(this.editForm, apidiaexcepcional);
  }
}
