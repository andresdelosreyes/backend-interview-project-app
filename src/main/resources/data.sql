  --devices
  insert into device values (1, TRUE, 'DELL XPS 13', 'WINDOWS_WORKSTATION');
  insert into device values (2, TRUE, 'MICROSOFT SURFACE', 'WINDOWS_WORKSTATION');
  insert into device values (3, TRUE, 'MACBOOK PRO', 'MAC');

  --services
  insert into service values (1, TRUE, 'ANTIVIRUS');
  insert into service values (2, TRUE, 'BACKUP');
  insert into service values (3, TRUE, 'PSA');
  insert into service values (4, TRUE, 'SCREEN SHARE');

  -- cost per device
  insert into cost values (1, 4, 1, null);
  insert into cost values (2, 4, 2, null);
  insert into cost values (3, 4, 3, null);

  --cost per device per ANTIVIRUS
  insert into cost values (4, 5, 1, 1);
  insert into cost values (5, 5, 2, 1);
  insert into cost values (6, 7, 3, 1);

  --cost per device per BACKUP
  insert into cost values (7, 3, 1, 2);
  insert into cost values (8, 3, 2, 2);
  insert into cost values (9, 3, 3, 2);

  --cost per device per PSA
  insert into cost values (10, 2, 1, 3);
  insert into cost values (11, 2, 2, 3);
  insert into cost values (12, 2, 3, 3);

  --cost per device per SCREEN SHARE
  insert into cost values (13, 1, 1, 4);
  insert into cost values (14, 1, 2, 4);
  insert into cost values (15, 1, 3, 4);