import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageTodo } from './manage-todo';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ActivatedRoute, provideRouter } from '@angular/router';

describe('ManageTodo', () => {
  let component: ManageTodo;
  let fixture: ComponentFixture<ManageTodo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageTodo],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: new Map([['id', '300346ec-6785-4d5e-90a0-07fa979e23f1']]) },
            params: { subscribe: (fn: any) => fn({ id: '300346ec-6785-4d5e-90a0-07fa979e23f1' }) },
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ManageTodo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
