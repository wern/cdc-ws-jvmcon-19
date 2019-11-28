import { Component } from '@angular/core';
import {Greeting, Greetings} from './Greetings';
import {GreetingService} from './greeting.service';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-consumer';
  greetings: Greetings = {greetings: []} as Greetings;
  private greetingForm: FormGroup;

  constructor(
    private greetingService: GreetingService,
    private formBuilder: FormBuilder
  ) {
    this.greetingForm = this.formBuilder.group({
      type: '',
      phrase: ''
    });
  }

  loadGreetings(): void {
    this.greetingService.loadGreetings()
      .subscribe(greetings => this.greetings = greetings);
  }

  deleteGreeting(greeting: Greeting) {
    this.greetingService.deleteGreeting(greeting)
      .subscribe(() => this.loadGreetings());
  }

  saveGreeting(greeting: Greeting) {
    this.greetingService.saveGreeting(greeting)
      .subscribe(() => this.loadGreetings());
  }
}
