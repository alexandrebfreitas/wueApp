import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IApiassistencia } from 'app/entities/apiassistencia/apiassistencia.model';
import { ApiassistenciaService } from 'app/entities/apiassistencia/service/apiassistencia.service';
import { AssistenciasService } from '../service/assistencias.service';
import { IAssistencias } from '../assistencias.model';
import { AssistenciasFormGroup, AssistenciasFormService } from './assistencias-form.service';

@Component({
  selector: 'jhi-assistencias-update',
  templateUrl: './assistencias-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AssistenciasUpdateComponent implements OnInit {
  isSaving = false;
  assistencias: IAssistencias | null = null;

  apiassistenciasSharedCollection: IApiassistencia[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected assistenciasService = inject(AssistenciasService);
  protected assistenciasFormService = inject(AssistenciasFormService);
  protected apiassistenciaService = inject(ApiassistenciaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AssistenciasFormGroup = this.assistenciasFormService.createAssistenciasFormGroup();

  compareApiassistencia = (o1: IApiassistencia | null, o2: IApiassistencia | null): boolean =>
    this.apiassistenciaService.compareApiassistencia(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assistencias }) => {
      this.assistencias = assistencias;
      if (assistencias) {
        this.updateForm(assistencias);
      }

      this.loadRelationshipsOptions();
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
    const assistencias = this.assistenciasFormService.getAssistencias(this.editForm);
    if (assistencias.id !== null) {
      this.subscribeToSaveResponse(this.assistenciasService.update(assistencias));
    } else {
      this.subscribeToSaveResponse(this.assistenciasService.create(assistencias));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssistencias>>): void {
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

  protected updateForm(assistencias: IAssistencias): void {
    this.assistencias = assistencias;
    this.assistenciasFormService.resetForm(this.editForm, assistencias);

    this.apiassistenciasSharedCollection = this.apiassistenciaService.addApiassistenciaToCollectionIfMissing<IApiassistencia>(
      this.apiassistenciasSharedCollection,
      assistencias.apiassistencia,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.apiassistenciaService
      .query()
      .pipe(map((res: HttpResponse<IApiassistencia[]>) => res.body ?? []))
      .pipe(
        map((apiassistencias: IApiassistencia[]) =>
          this.apiassistenciaService.addApiassistenciaToCollectionIfMissing<IApiassistencia>(
            apiassistencias,
            this.assistencias?.apiassistencia,
          ),
        ),
      )
      .subscribe((apiassistencias: IApiassistencia[]) => (this.apiassistenciasSharedCollection = apiassistencias));
  }
}
