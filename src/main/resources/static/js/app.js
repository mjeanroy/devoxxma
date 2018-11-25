import $ from 'jquery';

function forEach(array, iteratee) {
  for (let i = 0, size = array.length; i < size; ++i) {
    iteratee(array[i], i, array);
  }
}

function map(array, iteratee) {
  const results = [];
  for (let i = 0, size = array.length; i < size; ++i) {
    results.push(iteratee(array[i], i, array));
  }

  return results;
}

function every(array, iteratee) {
  for (let i = 0, size = array.length; i < size; ++i) {
    if (!iteratee(array[i], i, array)) {
      return false;
    }
  }

  return true;
}

const validators = {
  required($input) {
    const value = $input.val();
    const required = $input.prop('required');
    return !required || !!value;
  },

  maxlength($input) {
    const value = $input.val();
    const maxlength = $input.prop('maxlength');
    return !maxlength || maxlength < 0 || value.length <= maxlength;
  },
};

function checkInput($input) {
  return every(Object.keys(validators), (validatorName) => (
    validators[validatorName]($input)
  ));
}

function checkForm($form) {
  const $inputs = $form.find('input,textarea');

  let formValid = true;

  forEach($inputs, (input) => {
    const $input = $(input);
    const isValid = checkInput($input);

    formValid = formValid && isValid;
    $input.toggleClass('error', !isValid);
  });

  $form.toggleClass('error', !formValid);
  $form.find('[type="submit"]').prop('disabled', !formValid);
}

function bindForm($form) {
  $form.on('input', () => {
    checkForm($form);
  });
}

const handlers = {
  '/users': () => {
    $('#refresh-btn').on('click', (e) => {
      e.preventDefault();
      $.get('/api/users').done((response) => {
        $('tbody').html(map(response._embedded.users, (user) =>
            `<tr><td>${user.key}</td><td>${user.login}</td><td>${user.creationDate}</td></tr>`
        ));
      });
    })
  },
};

function getCurrentPath() {
  const location = window.location;
  const path = location.pathname || '/';
  return path.charAt(0) !== '/' ? `/${path}` : path;
}

function bootstrapForms() {
  forEach($(document).find('form'), (form) => {
    const $form = $(form);
    checkForm($form);
    bindForm($form);
  });
}

function bootstrapPage() {
  const path = getCurrentPath();
  const handlerFn = handlers[path];
  if (handlerFn) {
    handlerFn();
  }
}

$(document).ready(() => {
  bootstrapForms();
  bootstrapPage();
});
