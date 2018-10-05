const {resolve} = require('path');

module.exports = function (grunt) {

    const nodeBin = (bin) =>
    resolve(
        __dirname,
        'node_modules',
        '.bin',
        `${bin}${process.platform == 'win32' ? '.cmd' : ''}`
    );

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        clean: {
           dist: ['dist/*']
        },
        lambda_package: {
           default: {
             options: {
                 dist_folder: 'dist',
                 base_folder: 'src'
             }
           }
        }
    });

    grunt.loadNpmTasks('grunt-run');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-aws-lambda-package');

    grunt.registerTask('package', ['clean', 'lambda_package:default']);
};