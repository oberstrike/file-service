package de.ma.datafile.impl.utils

abstract class AbstractImplTest() :
    DataFileContentTest by DataFileContentTestImpl(),
    DataFileTest by DataFileTestImpl()

