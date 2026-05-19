/* Auth page JS — validation & password strength */

/* Regexes mirror server-side com.travelbooking.util.InputValidator */
const EMAIL_RE = /^[A-Za-z0-9+_.\-]+@[A-Za-z0-9.\-]+\.[A-Za-z]{2,}$/;
const PHONE_RE = /^\+?[0-9]{7,15}$/;

/* Heuristic blocks for obviously-fake email addresses */
const JUNK_LOCAL_PARTS = new Set([
    'test', 'tests', 'testing', 'test123', 'asdf', 'asdfgh', 'qwerty',
    'abc', 'abcd', 'abcde', 'aaa', 'aaaa', 'xxx', 'xxxx', 'foo', 'bar',
    'foobar', 'admin', 'user', 'sample', 'demo', 'dummy', 'fake',
    'noreply', 'no-reply', 'donotreply'
]);

const DISPOSABLE_DOMAINS = new Set([
    'mailinator.com', 'tempmail.com', 'temp-mail.org', '10minutemail.com',
    'guerrillamail.com', 'guerrillamail.net', 'sharklasers.com',
    'throwawaymail.com', 'yopmail.com', 'getnada.com', 'maildrop.cc',
    'trashmail.com', 'fakeinbox.com', 'dispostable.com', 'mintemail.com',
    'mohmal.com', 'mytemp.email', 'tempinbox.com'
]);

document.addEventListener('DOMContentLoaded', () => {
    const regForm = document.getElementById('registerForm');
    if (regForm) initRegisterValidation(regForm);

    const loginForm = document.getElementById('loginForm');
    if (loginForm) initLoginValidation(loginForm);
});

/* ---------- Field error UI helpers ---------- */
function setFieldError(input, message) {
    if (!input) return;
    const group = input.closest('.form-group');
    if (!group) return;
    let err = group.querySelector('.field-error');
    if (!err) {
        err = document.createElement('div');
        err.className = 'field-error';
        group.appendChild(err);
    }
    if (message) {
        input.classList.add('is-invalid');
        err.textContent = message;
        err.style.display = 'block';
    } else {
        input.classList.remove('is-invalid');
        err.textContent = '';
        err.style.display = 'none';
    }
}

function attachLiveValidation(input, validator) {
    if (!input) return;
    input.addEventListener('blur', () => setFieldError(input, validator(input)));
    input.addEventListener('input', () => {
        if (input.classList.contains('is-invalid')) {
            const msg = validator(input);
            if (!msg) setFieldError(input, null);
        }
    });
}

/* ---------- Validators ---------- */

/* Basic format check — used for login (lenient, accepts any well-formed email) */
function validateEmail(input) {
    const value = (input.value || '').trim();
    if (!value) return 'Email is required';
    if (value.length > 254) return 'Email is too long';
    if (value.includes('..')) return 'Email cannot contain consecutive dots';
    if (!EMAIL_RE.test(value)) return 'Enter a valid email like name@example.com';
    return null;
}

/* Strict check — used for registration. Rejects junk/disposable patterns. */
function validateEmailStrict(input) {
    const basic = validateEmail(input);
    if (basic) return basic;

    const value = input.value.trim().toLowerCase();
    const atIdx = value.indexOf('@');
    const local = value.substring(0, atIdx);
    const domain = value.substring(atIdx + 1);

    /* Local-part rules */
    if (local.length < 3) {
        return 'Email username must be at least 3 characters';
    }
    if (!/[a-z]/.test(local)) {
        return 'Email username must contain at least one letter';
    }
    if (/^\d+$/.test(local)) {
        return 'Email username cannot be only numbers';
    }
    if (local.startsWith('.') || local.endsWith('.')) {
        return 'Email username cannot start or end with a dot';
    }
    /* Same character repeated 4+ times: aaaa@, xxxx@ */
    if (/(.)\1{3,}/.test(local)) {
        return 'Email username looks fake (repeated characters)';
    }
    /* Sequential digits: 123, 1234, 12345 as the entire local part */
    if (/^(0?1234|01234|12345|123456|123|1234)\d*$/.test(local)) {
        return 'Email username looks fake';
    }
    if (JUNK_LOCAL_PARTS.has(local)) {
        return 'Please use your real email address';
    }

    /* Domain rules */
    if (DISPOSABLE_DOMAINS.has(domain)) {
        return 'Disposable email addresses are not allowed';
    }
    /* Domain must have at least one letter (rejects e.g. 1.23) */
    if (!/[a-z]/.test(domain)) {
        return 'Enter a valid email domain';
    }

    return null;
}

function validateRequiredText(input, label, min) {
    const value = (input.value || '').trim();
    if (!value) return `${label} is required`;
    if (min && value.length < min) return `${label} must be at least ${min} characters`;
    if (value.length > 50) return `${label} is too long`;
    return null;
}

function validatePhone(input) {
    const value = (input.value || '').trim();
    if (!value) return 'Phone number is required';
    if (!PHONE_RE.test(value)) return 'Enter 7-15 digits, optional leading +';
    return null;
}

function validatePassword(input, min) {
    const value = input.value || '';
    if (!value) return 'Password is required';
    if (value.length < (min || 6)) return `Password must be at least ${min || 6} characters`;
    return null;
}

/* ---------- Login form ---------- */
function initLoginValidation(form) {
    const email = form.querySelector('[name="email"]');
    const pw = form.querySelector('[name="password"]');

    attachLiveValidation(email, validateEmail);
    attachLiveValidation(pw, (i) => i.value ? null : 'Password is required');

    form.addEventListener('submit', (e) => {
        const checks = [
            { input: email, msg: validateEmail(email) },
            { input: pw, msg: pw.value ? null : 'Password is required' }
        ];
        let firstError = null;
        for (const c of checks) {
            setFieldError(c.input, c.msg);
            if (c.msg && !firstError) firstError = c;
        }
        if (firstError) {
            e.preventDefault();
            showToast(firstError.msg, 'danger');
            firstError.input.focus();
        }
    });
}

/* ---------- Register form ---------- */
function initRegisterValidation(form) {
    const fn = form.querySelector('[name="firstName"]');
    const ln = form.querySelector('[name="lastName"]');
    const email = form.querySelector('[name="email"]');
    const phone = form.querySelector('[name="phone"]');
    const pw = form.querySelector('[name="password"]');
    const cpw = form.querySelector('[name="confirmPassword"]');
    const strengthBar = document.querySelector('.password-strength-bar');

    /* Password strength meter */
    if (pw && strengthBar) {
        pw.addEventListener('input', () => {
            const v = pw.value;
            strengthBar.className = 'password-strength-bar';
            const hasUpper = /[A-Z]/.test(v);
            const hasLower = /[a-z]/.test(v);
            const hasDigit = /[0-9]/.test(v);
            const hasSymbol = /[^A-Za-z0-9]/.test(v);
            const score = [hasUpper, hasLower, hasDigit, hasSymbol].filter(Boolean).length;
            if (v.length >= 10 && score >= 3) {
                strengthBar.classList.add('strength-strong');
            } else if (v.length >= 8 && score >= 2) {
                strengthBar.classList.add('strength-medium');
            } else if (v.length >= 6) {
                strengthBar.classList.add('strength-weak');
            } else if (v.length > 0) {
                strengthBar.classList.add('strength-weak');
            }
        });
    }

    /* Confirm-password validator (re-runs whenever password changes) */
    const cpwValidator = () => {
        if (!cpw.value) return 'Please confirm your password';
        if (cpw.value !== pw.value) return 'Passwords do not match';
        return null;
    };

    attachLiveValidation(fn, (i) => validateRequiredText(i, 'First name', 2));
    attachLiveValidation(ln, (i) => validateRequiredText(i, 'Last name', 2));
    attachLiveValidation(email, validateEmailStrict);
    attachLiveValidation(phone, validatePhone);
    attachLiveValidation(pw, (i) => validatePassword(i, 6));
    if (cpw) {
        attachLiveValidation(cpw, cpwValidator);
        pw.addEventListener('input', () => {
            if (cpw.value) {
                const msg = cpwValidator();
                setFieldError(cpw, msg);
            }
        });
    }

    form.addEventListener('submit', (e) => {
        const checks = [
            { input: fn, msg: validateRequiredText(fn, 'First name', 2) },
            { input: ln, msg: validateRequiredText(ln, 'Last name', 2) },
            { input: email, msg: validateEmailStrict(email) },
            { input: phone, msg: validatePhone(phone) },
            { input: pw, msg: validatePassword(pw, 6) },
            { input: cpw, msg: cpwValidator() }
        ];
        let firstError = null;
        for (const c of checks) {
            setFieldError(c.input, c.msg);
            if (c.msg && !firstError) firstError = c;
        }
        if (firstError) {
            e.preventDefault();
            showToast(firstError.msg, 'danger');
            firstError.input.focus();
        }
    });
}
