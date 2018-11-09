const path = require('path');
const fs = require('fs-extra');
const gulp = require('gulp');
const log = require('fancy-log');
const colors = require('ansi-colors');
const del = require('del');
const pkg = require('./package.json');

const ROOT = __dirname;
const NODE_MODULES = path.join(ROOT, 'node_modules');
const SRC = path.join(ROOT, 'src', 'main', 'webapp');
const VENDORS = path.join(SRC, 'vendors');

gulp.task('clean', () => (
    del([VENDORS])
));

gulp.task('vendors', ['clean'], () => {
    fs.ensureDirSync(VENDORS);

    Object.keys(pkg.dependencies).forEach((id) => {
        const src = path.join(NODE_MODULES, id);
        const dest = path.join(VENDORS, id);

        log(colors.gray(`Copying ${id} from ${src} to ${dest}...`));
        fs.copySync(src, dest);
    });
});
