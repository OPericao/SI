close all;
clear all;
warning('off');

%%%%%%%%%%%%%%%%%%%%%%
% LOAD THE DATA  %
%%%%%%%%%%%%%%%%%%%%%%
% Read the stocks market for amn specific quarter of the year
stock_data = readtable('IBEX_2017.csv');
stock_data = stock_data(90:120,3:end).Variables;

[num_days, num_assets] = size(stock_data);
num_variables = num_days*num_assets;

initial_amount = 1000;


%%%%%%%%%%%%%%%%%%%%%%%%%%
%  OPTIONS FOR THE TEST  %
%%%%%%%%%%%%%%%%%%%%%%%%%%
options = gaoptimset();
options = gaoptimset(options, 'PopInitRange',[zeros(1, num_assets); 0.1*ones(1,num_assets)]);       % Initial Range
options = gaoptimset(options, 'PopulationType' , 'doubleVector');                                   % Population Type
options = gaoptimset(options, 'PopulationSize' , 30);                                               % Polulation Size
options = gaoptimset(options, 'Generations',500);                                                   % Generation Limit
options = gaoptimset(options, 'StallTimeLimit', 1000);                                              % Time limit
options = gaoptimset(options, 'EliteCount', 1);                                                     % Elitism
options = gaoptimset(options, 'CreationFcn ', @gacreationlinearfeasible);                           % How the individuals are created
options = gaoptimset(options, 'SelectionFcn', @selectiontournament);                                % Selection Method
options = gaoptimset(options, 'CrossoverFcn', @crossoverarithmetic);                                  % Crossover Function
options = gaoptimset(options, 'CrossoverFraction', 0.7);                                            % Crossover rate
options = gaoptimset(options, 'MutationFcn', {@mutationuniform, 0.1});                              % Mutation Function & Probability
options = gaoptimset(options, 'UseParallel', true);                                                 % If the algorithm is parallelized

%%%%%%%%%%%%%%%%%%%%
%  Display Setup   %
%%%%%%%%%%%%%%%%%%%%
%options = gaoptimset(options, 'Display', 'off');
options = gaoptimset(options, 'PlotInterval', 5);
options = gaoptimset(options,'PlotFcns',{@gaplotstopping, @gaplotscorediversity, @gaplotbestf});


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  FITNESS FUNC AND RESTRICTIONS   %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% This is the function used in order to evaluate the performance of a
% particular distributions among shares in percentage among the data
fitnessfcn = @(x) -compute_portfolio_return(x, stock_data);


fitness = fitnessfcn(stock_data);

lb = zeros(num_variables, 1);                                                                      % Lower threshold for the weights 
ub = ones(num_variables,1);                                                                        % Upper threshold for the weights

%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  ALGORITHM EXECUTION    %
%%%%%%%%%%%%%%%%%%%%%%%%%%%
 [solution,fval,exitflag,output,population,scores] = ...
          ga(fitnessfcn, num_assets, ...                                                           % fitness_fun, nvars
             [], [],...                                                                            % inequalities A*x<=b 
             [],[],...                                                                             % equalities A*x=b
             lb,ub,...                                                                             % lb and ub
             [], options);                                                                         % nonlcon,options



%%%%%%%%%%%%%
%  RESULTS  %
%%%%%%%%%%%%%
% Calculate the performance of the best individual
[portfolio_return, portfolio_std] = compute_portfolio_stats(solution, stock_data);

% Show the results
fprintf('Weights of the different assets in the portfolio:\n');
disp(solution');
fprintf('Performance of the portfolio: %f\n', portfolio_return);
fprintf('Risk of the portfolio (STD): %f\n', portfolio_std);

