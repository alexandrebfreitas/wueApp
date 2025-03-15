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
import { ApiindicadorService } from '../service/apiindicador.service';
import { IApiindicador } from '../apiindicador.model';
import { ApiindicadorFormGroup, ApiindicadorFormService } from './apiindicador-form.service';

@Component({
  selector: 'jhi-apiindicador-update',
  templateUrl: './apiindicador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ApiindicadorUpdateComponent implements OnInit {
  isSaving = false;
  apiindicador: IApiindicador | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected apiindicadorService = inject(ApiindicadorService);
  protected apiindicadorFormService = inject(ApiindicadorFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ApiindicadorFormGroup = this.apiindicadorFormService.createApiindicadorFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apiindicador }) => {
      this.apiindicador = apiindicador;
      if (apiindicador) {
        this.updateForm(apiindicador);
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
    const apiindicador = this.apiindicadorFormService.getApiindicador(this.editForm);
    if (apiindicador.id !== null) {
      this.subscribeToSaveResponse(this.apiindicadorService.update(apiindicador));
    } else {
      this.subscribeToSaveResponse(this.apiindicadorService.create(apiindicador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiindicador>>): void {
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

  protected updateForm(apiindicador: IApiindicador): void {
    this.apiindicador = apiindicador;
    this.apiindicadorFormService.resetForm(this.editForm, apiindicador);
  }
}
