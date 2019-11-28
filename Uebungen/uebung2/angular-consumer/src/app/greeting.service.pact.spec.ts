import {TestBed} from '@angular/core/testing';
import {HttpClientModule, HttpErrorResponse} from '@angular/common/http';
import {GreetingService} from './greeting.service';
import {PactWeb} from '@pact-foundation/pact-web';
import {Greeting} from './Greetings';

const path = require('path');

describe('GreetingService', () => {

  let provider;

  beforeAll(done => {
    // TODO init mock provider
  });

  afterAll(done => {
    // TODO write pact after tests
  });

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule
      ],
      providers: [
        GreetingService
      ],
    });
  });

  afterEach((done) => {
    // TODO check if interaction called after each test
  });

  describe('load certain greeting type()', () => {
    // TODO write interaction and test for GET /greetings/casual

  });

  describe('greeting not found()', () => {

    beforeAll((done) => {
      provider.addInteraction({
        uponReceiving: 'Abfrage eines nicht existierenden Grusses',
        withRequest: {
          method: 'GET',
          path: '/greetings/formal'
        },
        willRespondWith: {
          status: 404
        }
      }).then(done, error => done.fail(error));
    });

    it('should return error 404', (done) => {
      const greetingService: GreetingService = TestBed.get(GreetingService);
      greetingService.loadGreetingWithType('formal').subscribe(response => {
        done.fail();
      }, (error: HttpErrorResponse) => {
        if (error.status === 404) {
          done();
        } else {
          done.fail();
        }
      });
    });

  });

  describe('delete a greeting()', () => {

    beforeAll((done) => {
      provider.addInteraction({
        uponReceiving: 'Loeschen eines Grusses',
        withRequest: {
          method: 'DELETE',
          path: '/greetings/formal'
        },
        willRespondWith: {
          status: 200
        }
      }).then(done, error => done.fail(error));
    });

    it('should delete a greeting', (done) => {
      const greetingService: GreetingService = TestBed.get(GreetingService);
      greetingService.deleteGreeting(new Greeting('formal', '')).subscribe(response => {
        expect(response).toEqual(200);
        done();
      }, error => {
        done.fail(error);
      });
    });

  });

  describe('save a greeting()', () => {

    beforeAll((done) => {
      provider.addInteraction({
        uponReceiving: 'Speichern eines neuen Grusses',
        withRequest: {
          method: 'PUT',
          path: '/greetings/formal',
          body: {
            greeting: {
              type: 'formal',
              phrase: 'Good day'
            }
          }
        },
        willRespondWith: {
          status: 200
        }
      }).then(done, error => done.fail(error));
    });

    it('should save a greeting', (done) => {
      const greetingService: GreetingService = TestBed.get(GreetingService);
      greetingService.saveGreeting(new Greeting('formal', 'Good day')).subscribe(response => {
        expect(response).toEqual(200);
        done();
      }, error => {
        done.fail(error);
      });
    });

  });

  describe('load all greetings()', () => {

    beforeAll((done) => {
      provider.addInteraction({
        uponReceiving: 'Abfrage aller Gruesse mit vorhandenen Daten',
        withRequest: {
          method: 'GET',
          path: '/greetings'
        },
        willRespondWith: {
          status: 200,
          body: {
            greetings: [
              {
                type: 'formal',
                phrase: 'Good morning'
              },
              {
                type: 'casual',
                phrase: 'Hey'
              }
            ]
          }
        }
      }).then(done, error => done.fail(error));
    });

    const expectedGreetings = {greetings: [{type: 'formal', phrase: 'Good morning'}, {type: 'casual', phrase: 'Hey'}]};

    it('should load all greetings', (done) => {
      const greetingService: GreetingService = TestBed.get(GreetingService);
      greetingService.loadGreetings().subscribe(response => {
        expect(response).toEqual(expectedGreetings);
        done();
      }, error => {
        done.fail(error);
      });
    });

  });

});
