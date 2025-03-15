import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IApiassistencia } from 'app/entities/apiassistencia/apiassistencia.model';
import { ApiassistenciaService } from 'app/entities/apiassistencia/service/apiassistencia.service';
import { AssistenciasService } from '../service/assistencias.service';
import { IAssistencias } from '../assistencias.model';
import { AssistenciasFormService } from './assistencias-form.service';

import { AssistenciasUpdateComponent } from './assistencias-update.component';

describe('Assistencias Management Update Component', () => {
  let comp: AssistenciasUpdateComponent;
  let fixture: ComponentFixture<AssistenciasUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let assistenciasFormService: AssistenciasFormService;
  let assistenciasService: AssistenciasService;
  let apiassistenciaService: ApiassistenciaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AssistenciasUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AssistenciasUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssistenciasUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    assistenciasFormService = TestBed.inject(AssistenciasFormService);
    assistenciasService = TestBed.inject(AssistenciasService);
    apiassistenciaService = TestBed.inject(ApiassistenciaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Apiassistencia query and add missing value', () => {
      const assistencias: IAssistencias = { id: 32442 };
      const apiassistencia: IApiassistencia = { id: 17411 };
      assistencias.apiassistencia = apiassistencia;

      const apiassistenciaCollection: IApiassistencia[] = [{ id: 17411 }];
      jest.spyOn(apiassistenciaService, 'query').mockReturnValue(of(new HttpResponse({ body: apiassistenciaCollection })));
      const additionalApiassistencias = [apiassistencia];
      const expectedCollection: IApiassistencia[] = [...additionalApiassistencias, ...apiassistenciaCollection];
      jest.spyOn(apiassistenciaService, 'addApiassistenciaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assistencias });
      comp.ngOnInit();

      expect(apiassistenciaService.query).toHaveBeenCalled();
      expect(apiassistenciaService.addApiassistenciaToCollectionIfMissing).toHaveBeenCalledWith(
        apiassistenciaCollection,
        ...additionalApiassistencias.map(expect.objectContaining),
      );
      expect(comp.apiassistenciasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const assistencias: IAssistencias = { id: 32442 };
      const apiassistencia: IApiassistencia = { id: 17411 };
      assistencias.apiassistencia = apiassistencia;

      activatedRoute.data = of({ assistencias });
      comp.ngOnInit();

      expect(comp.apiassistenciasSharedCollection).toContainEqual(apiassistencia);
      expect(comp.assistencias).toEqual(assistencias);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssistencias>>();
      const assistencias = { id: 7841 };
      jest.spyOn(assistenciasFormService, 'getAssistencias').mockReturnValue(assistencias);
      jest.spyOn(assistenciasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assistencias });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assistencias }));
      saveSubject.complete();

      // THEN
      expect(assistenciasFormService.getAssistencias).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(assistenciasService.update).toHaveBeenCalledWith(expect.objectContaining(assistencias));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssistencias>>();
      const assistencias = { id: 7841 };
      jest.spyOn(assistenciasFormService, 'getAssistencias').mockReturnValue({ id: null });
      jest.spyOn(assistenciasService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assistencias: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assistencias }));
      saveSubject.complete();

      // THEN
      expect(assistenciasFormService.getAssistencias).toHaveBeenCalled();
      expect(assistenciasService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssistencias>>();
      const assistencias = { id: 7841 };
      jest.spyOn(assistenciasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assistencias });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(assistenciasService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareApiassistencia', () => {
      it('Should forward to apiassistenciaService', () => {
        const entity = { id: 17411 };
        const entity2 = { id: 9746 };
        jest.spyOn(apiassistenciaService, 'compareApiassistencia');
        comp.compareApiassistencia(entity, entity2);
        expect(apiassistenciaService.compareApiassistencia).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
