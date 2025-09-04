import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverviewTodos } from './overview-todos';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';

describe('OverviewTodos', () => {
  let component: OverviewTodos;
  let fixture: ComponentFixture<OverviewTodos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OverviewTodos],
      providers: [provideHttpClient(), provideHttpClientTesting(), provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(OverviewTodos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
