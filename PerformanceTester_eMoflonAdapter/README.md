# eMoflon-PerformanceTests

0. Install GraphViz http://www.graphviz.org/Download..php
1. Install Gurobi 7.0.2 (make sure it is exactly this version!) http://www.gurobi.com/downloads/gurobi-optimizer
2. Get the latest version of the Eclipse Modeling Tools:  http://www.eclipse.org/downloads/packages/
3. Install eMoflon from http://emoflon.org/
4. Check encoding for Xtend Files
    - In Eclipse: Go to ```Window->Preferences->General->Workspace```
    - Change the text file encoding to 'Other: UTF-8'
5. Install ```MoDisco/MoDisco SDK```(not incubation) and ```MoDisco use Cases/MoDisco Simple transformations chain (Incubation)``` from this update site http://download.eclipse.org/modeling/mdt/modisco/updates/release/
6. Install XTend from the Eclipse marketplace
7. Import all TGG-Projects and their dependencies into your workspace. They should all be located inside the same folder on the disk (for example by copying them into the workspace)
8. Import PerformanceTester and PerformanceTester_eMoflonAdapter from this repository
9. Adjust basePath in PerformanceTester_eMoflonAdapter/src/preformanceTester/eMoflon/factories/EMoflonPerformanceTggFactory.java to point to the directory with the tggs
10. Run /PerformanceTester_eMoflonAdapter/src/preformanceTester/eMoflon/generators/LaunchGroup.xtend as Java Application
11. Run /PerformanceTester_eMoflonAdapter/performance/AllPerformanceTests.launch as AllPerformanceTests

Add new TGGs:
